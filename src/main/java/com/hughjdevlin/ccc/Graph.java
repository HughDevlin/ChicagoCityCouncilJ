package com.hughjdevlin.ccc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
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
import org.xml.sax.SAXException;

import com.hughjdevlin.BlueprintsGraph;
import com.hughjdevlin.ccc.page.AbstractWebDriverPage;
import com.hughjdevlin.ccc.page.CalendarPage;
import com.hughjdevlin.ccc.page.MeetingPage;
import com.hughjdevlin.ccc.page.PeoplePage;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

public class Graph extends BlueprintsGraph {

	/**
	 * constructor
	 * @param dir
	 */
	public Graph(String dir) {
		super(dir);
	}
	
	private static void addActors(Graph g) throws IOException, ParserConfigurationException, SAXException {
		PeoplePage peoplePage = new PeoplePage();
		List<Person> people = peoplePage.persons();
		// peoplePage.close();
		for(Person person : people) {
			Vertex v = g.addVertex(null);
			v.setProperty("name", person.getName());
			v.setProperty("role", person.getRole());
			v.setProperty("url", AbstractWebDriverPage.getUrl(person.getUrl()));
		}		
	}
	
	private static void addLegislation(Graph g) throws MalformedURLException, InterruptedException {
		GremlinPipeline pipe = new GremlinPipeline(g.getVertices());
		for(Vertex meeting : (List<Vertex>) (pipe.hasNot("date", null).toList())) {
			System.out.println("Adding legislation for " + meeting.getProperty("date"));
			MeetingPage meetingPage = new MeetingPage(AbstractWebDriverPage.getUrl(meeting.getProperty("url").toString()));
			do {
				System.out.println("Page " + meetingPage.page() + " of " + meetingPage.pages());
				List<Legislation> legislation = meetingPage.legislation();
				for(Legislation l : legislation) {
					System.out.println("Adding legislation " + l.getName());
					Vertex v = g.addVertex(null);
					v.setProperty("name", l.getName());
					v.setProperty("title", l.getTitle());
					v.setProperty("status", l.getStatus());
					v.setProperty("result", l.getResult());
					v.setProperty("url", AbstractWebDriverPage.getUrl(l.getUrl()));
					v.setProperty("voteUrl", AbstractWebDriverPage.getUrl(l.getVoteUrl()));
					g.addEdge(null, meeting, v, "date");
				}			
//				break; // debug
				// page meeting page
				if(meetingPage.page() == meetingPage.pages())
					break;
				meetingPage.next();
			} while(true);
			meetingPage.close();
		} // end for
	}
	
	private static void addMeetings(Graph g, Date start, Date end) throws ParseException, MalformedURLException {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CalendarPage calendarPage = new CalendarPage();
		Map<Date, URL> meetings = calendarPage.meetings();
		calendarPage.close();
		for(Map.Entry<Date, URL> meeting : meetings.entrySet()) {
			Date date = meeting.getKey();
			if(date.before(start))
				continue;
			if(date.after(end))
				continue;
			System.out.println("Adding meeting " + dateFormat.format(date));
			Vertex v = g.addVertex(null);
			v.setProperty("date", dateFormat.format(date));
			v.setProperty("url", AbstractWebDriverPage.getUrl(meeting.getValue()));
		}		
	}
	
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws org.apache.commons.cli.ParseException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ParseException, ParserConfigurationException, SAXException, IOException, org.apache.commons.cli.ParseException, InterruptedException {
		final String dateFormatString = "yyyy-MM-dd";
		final DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Options options = new Options();
		options.addOption(OptionBuilder.withArgName("path").hasArg().withDescription("database directory").create("database"));
		options.addOption("help", false, "display this help message and quit");
		options.addOption("new", false, "delete and recreate database");
		options.addOption("actors", false, "add actors");
		options.addOption("meetings", false, "add meetings");
		options.addOption("legislation", false, "add legislation");
		options.addOption("votes", false, "add votes");
		options.addOption("pdfs", false, "add pdfs");
		options.addOption(OptionBuilder.withArgName(dateFormatString).hasArg().withDescription("start meeting date").create("start"));
		options.addOption(OptionBuilder.withArgName(dateFormatString).hasArg().withDescription("end meeting date").create("end"));
		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
		if(cmd.hasOption("help")) {
			formatter.printHelp( "Graph", options );
			return;
		}
		String database = cmd.hasOption("database") ? cmd.getOptionValue("database") : "db/ccc";
		Date start = dateFormat.parse(cmd.hasOption("start") ? cmd.getOptionValue("start") : "2013-02-01");
		Date end = dateFormat.parse(cmd.hasOption("end") ? cmd.getOptionValue("end") : "2013-03-01");
				
		if(cmd.hasOption("new")) {
			System.out.println("Deleting database");
			Graph.delete(database);			
		}
		
		Graph g = new Graph(database);
		System.out.println("Graph features:\n" + g.getFeatures());
		
		// Add actors
		if(cmd.hasOption("actors")) {
			System.out.println("Adding actors");
			addActors(g);
		}
		
		// Add meetings
		if(cmd.hasOption("meetings")) {
			System.out.println("Adding meetings");
			addMeetings(g, start, end);
		}
		
		// Add legislation
		if(cmd.hasOption("legislation")) {
			System.out.println("Adding legislation");
			addLegislation(g);
		}
		
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();
//		GremlinPipeline pipe = new GremlinPipeline(g.getVertices());
//		for(Vertex v : (List<Vertex>) (pipe.hasNot("voteUrl", null).toList())) {
//			System.out.println(v.getProperty("voteUrl"));
//			Document doc = db.parse(AbstractPage.getUrl(v.getProperty("voteUrl").toString()).toString());
//			System.out.println(doc.getElementsByTagName("title").item(0).getTextContent());
//		}
		System.out.println("Graph description:\n" + g.getDescription());
		g.shutdown();
	}

}
