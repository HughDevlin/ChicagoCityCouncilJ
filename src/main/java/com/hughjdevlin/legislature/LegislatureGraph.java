/*
 * Gremlin
 * http://github.com/tinkerpop/gremlin
 * http://www.tinkerpop.com/docs/javadocs/gremlin/2.2.0/index.html
 * 
 */
package com.hughjdevlin.legislature;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiMap;
import org.xml.sax.SAXException;

import com.hughjdevlin.GremlinGraph;
import com.hughjdevlin.legislature.page.AbstractPage;
import com.hughjdevlin.legislature.page.CalendarPage;
import com.hughjdevlin.legislature.page.MeetingPage;
import com.hughjdevlin.legislature.page.LegislatorPage;
import com.hughjdevlin.legislature.page.VotePage;
import com.tinkerpop.gremlin.process.T;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.structure.Vertex;

public class LegislatureGraph extends GremlinGraph {
	final static String dateFormatString = "yyyy-MM-dd";
	final static DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

	/**
	 * constructor
	 * @param dir
	 */
	public LegislatureGraph(String file) {
		super(file);
	}
	
	private void addLegislators(List<Legislator> people) {
		for(Legislator legislator : people) {
			String url = AbstractPage.toPath(legislator.getUrl());
			System.out.println("Adding legislator " + legislator.getName() + " " + legislator.getRole() + " " + url);
			g.addVertex("type", "legislator",
				"name", legislator.getName(),
				"role", legislator.getRole(),
				"url", url
			);
		}		
	}
	
	private void addMeetings(MultiMap<Date, URL> meetings, final Date start, final Date end) {
		MapIterator it = meetings.mapIterator();
		while(it.hasNext()) {
			Date date = (Date) it.next();
			if(date.before(start))
				continue;
			if(date.after(end))
				continue;
			List<URL> urls = (List<URL>) it.getValue();
			for(URL url : urls) {
				String urlString = AbstractPage.toPath(url);
				String dateString = dateFormat.format(date);
				System.out.println("Adding meeting " + dateString + " " + urlString);
				g.addVertex("type", "meeting",
					"url", urlString,
					"date", dateString
				);
			}
		}
	}

	private void addLegislation() throws MalformedURLException, InterruptedException {
		Traversal<Vertex, Vertex> meetings = g.V().has("type", "meeting");
		for(Vertex meeting : meetings.toList()) {
			String date = meeting.property("date").value().toString();
			// MeetingDetail.aspx
			String url =  meeting.property("url").value().toString();
			System.out.println("Adding legislation for " + date + " " + url);
			MeetingPage meetingPage = new MeetingPage(AbstractPage.toUrl(url));
			do {
				System.out.println("Page " + meetingPage.page() + " of " + meetingPage.pages());
				List<Legislation> 
				legislationList = meetingPage.legislation();
				for(Legislation legislation : legislationList) {
					String legislationUrl = AbstractPage.toPath(legislation.getUrl());
					String voteUrl = AbstractPage.toPath(legislation.getVoteUrl());
					System.out.println("Adding legislation " + legislation.getName() + " " + legislationUrl);
					Vertex v = g.addVertex("type", "legislation",
						"url", legislationUrl,
						"name", legislation.getName(),
						"subtype", legislation.getType(),
						"title", legislation.getTitle(),
						"status", legislation.getStatus(),
						"result", legislation.getResult(),
						"voteUrl", voteUrl
					);
					meeting.addEdge("meeting-legislation", v);
				}			
				// break; // debug
				// next page?
				if(meetingPage.page() == meetingPage.pages())
					break;
				meetingPage.next();
			} while(true);
			meetingPage.close();
		} // end for
	}
	
	private void addVotes() throws ParserConfigurationException, IOException, SAXException {
		Traversal<Vertex, Vertex> legislations = g.V().has("type", "legislation");
		for(Vertex legislation : legislations.toList()) {
			String legislationName = legislation.property("name").value().toString();
			// HistoryDetail.aspx
			String voteUrl = legislation.property("voteUrl").value().toString();
			System.out.println("Adding votes for " + legislationName + " " + voteUrl);
			VotePage votePage = new VotePage(voteUrl);
			Map<String, String> votes = votePage.votes();
			votePage.close();
			for(Map.Entry<String, String> vote : votes.entrySet()) {
				String name = vote.getKey();
				String value = vote.getValue();
				Vertex legislator = (Vertex) g.V().has("type", "actor").has("name", name).next();
				legislator.addEdge("legislator-legislation", legislation, "vote", value);
			} // end for vote
																																																																break; //  debug
		} // end for legislation
	}
		
	public Map<String, Object> getDescription() {
		Map<String, Object> result = super.getDescription();
		result.put("legislators", g.V().has("type", "legislator").toList().size());
		result.put("meetings", g.V().has("type", "meeting").toList().size());
		result.put("legislation", g.V().has("type", "legislation").toList().size());
		result.put("meeting-legislation edges", g.E().has(T.label, "meeting-legislation").toList().size());
		result.put("legislator-legislation edges", g.E().has(T.label, "legislator-legislation").toList().size());
//		result.put("edge ids", g.E().id().dedup().count());
		return result;
	}

	/**
	 * Apache Commons Command Line Interface
	 * http://commons.apache.org/proper/commons-cli/
	 * http://commons.apache.org/proper/commons-cli/javadocs/api-release/index.html
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(OptionBuilder.withArgName("path").hasArg().withDescription("database file").create("database"));
		options.addOption("help", false, "display this help message and quit");
		options.addOption("new", false, "delete and recreate database");
		options.addOption("legislators", false, "add legislators");
		options.addOption("meetings", false, "add meetings");
		options.addOption("legislation", false, "add legislation");
		options.addOption("votes", false, "add votes");
		options.addOption("sponsors", false, "add sponsors and pdfs");
		options.addOption(OptionBuilder.withArgName(dateFormatString).hasArg().withDescription("start meeting date").create("start"));
		options.addOption(OptionBuilder.withArgName(dateFormatString).hasArg().withDescription("end meeting date").create("end"));
		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);
		if(cmd.hasOption("help")) {
			formatter.printHelp("Graph", options);
			return;
		}
		String database = cmd.hasOption("database") ? cmd.getOptionValue("database") : "db/legislature.graphml";
		Date start = dateFormat.parse(cmd.hasOption("start") ? cmd.getOptionValue("start") : "2014-02-01");
		Date end = dateFormat.parse(cmd.hasOption("end") ? cmd.getOptionValue("end") : "2014-03-01");
				
		LegislatureGraph g = new LegislatureGraph(database);

		if(cmd.hasOption("new")) {
			System.out.println("Deleting database");
		} else {
			g.read();
			System.out.println("Graph description:\n" + g.descriptionToString(g.getDescription()));
		}

		
		if(cmd.hasOption("legislators")) {
			System.out.println("Adding legislators");
			// People.aspx
			LegislatorPage legislatorPage = new LegislatorPage();
			List<Legislator> legislators = legislatorPage.legislators();
			legislatorPage.close();
			g.addLegislators(legislators);
		}
		
		if(cmd.hasOption("meetings")) {
			System.out.println("Adding meetings");
			// Calendar.aspx
			CalendarPage calendarPage = new CalendarPage();
			MultiMap<Date, URL> meetings = calendarPage.meetings();
			calendarPage.close();
			g.addMeetings(meetings, start, end);
		}
		
		if(cmd.hasOption("legislation")) {
			System.out.println("Adding legislation");
			g.addLegislation();
		}
		
		if(cmd.hasOption("votes")) {
			System.out.println("Adding votes");
			g.addVotes();
		}
		
		System.out.println("Graph description:\n" + g.descriptionToString(g.getDescription()));
		
		g.write();
		g.close();
		System.out.println("Done.");
	} // end main

} // end class
