package accessibility.adiff;

import java.util.LinkedList;

import javax.speech.synthesis.JSMLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.speech.engine.synthesis.JSMLParser;
import com.sun.speech.freetts.FreeTTSSpeakable;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

public class Test {

	//FreeTTS: http://freetts.sourceforge.net/docs/index.php
	//Diff Algorithm: https://code.google.com/archive/p/google-diff-match-patch/
	//JSML: https://www.w3.org/TR/jsml/#15974
	
	public static void main(String[] args) throws ParserConfigurationException, JSMLException {
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice helloVoice = voiceManager.getVoice("kevin16");
		Voice helloVoice2 = voiceManager.getVoice("kevin");
		helloVoice.allocate();
		helloVoice2.allocate();
		
		diff_match_patch differ = new diff_match_patch();
		LinkedList<Diff> diffs = differ.diff_main("hello world my name is cinali", "hello mars my name is watney");
		
		
		
//		helloVoice.speak("hello mars my name is watney");


//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();
//		Document doc = dBuilder.newDocument();
//		Element rootElement = doc.createElement("jsml");
//		doc.appendChild(rootElement);
//		Element divElement = doc.createElement("div");
//		rootElement.appendChild(divElement);
//		divElement.appendChild(doc.createTextNode("Message from <emphasis>George</emphasis> arrived."));
		
		
//		String jsmlAsString =
//				"<?xml version=\"1.0\"?><jsml>\n" +
//						"<div type=\"paragraph\">Message from\n" +
//						"Answer <marker mark=\"yes_no_prompt\"/> yes or no." +
//						"$88.80<sayas class=\"date:my\">7/99</sayas>\n" +
//						"<emphasis>Alan Schwarz</emphasis> about new synthesis\n" +
//						"technology. Arrived at <sayas class=\"time\">2pm</sayas>\n" +
//						"today.</div>\n" +
//						"\n" +
//						"<div type=\"paragraph\">I've attached a diagram showing the new way we do speech synthesis.</div>" +
//						"\n" +
//						"<div>Regards, Alan, <emphasis>Alan</emphasis>, Alan, Alan.</div>" +
//						"\n" +
//						"</jsml>";
//		JSMLParser parser = new JSMLParser(jsmlAsString, true);
//		Document doc2 = parser.getDocument(); 
//
//		helloVoice.speak(doc2);
        
//		<div type="paragraph">Message from
//				<emphasis>Alan Schwarz</emphasis> about new synthesis
//				technology. Arrived at <sayas class="time">2pm</sayas>
//				today.</div> 

		
		
		differ.diff_cleanupSemantic(diffs);
		String soFar = "";
		for (Diff d : diffs) {
			System.out.println(d);
			switch (d.operation) {
			case EQUAL:
				soFar += d.text;
				break;
			case DELETE:
				//helloVoice.speak("removed");
				//helloVoice.speak(d.text);
				break;
			case INSERT:
				helloVoice.speak(soFar);
				helloVoice2.speak("insert");
				helloVoice.speak(d.text);
				soFar = "";
				break;
			}
			
		}

		listAllVoices();
		x();
		
		helloVoice.deallocate();
		helloVoice2.deallocate();
	}

	public static void x() {
		String voiceName = "kevin16";

		System.out.println();
		System.out.println("Using voice: " + voiceName);

		/* The VoiceManager manages all the voices for FreeTTS.
		 */
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice helloVoice = voiceManager.getVoice(voiceName);

		if (helloVoice == null) {
			System.err.println(
					"Cannot find a voice named "
							+ voiceName + ".  Please specify a different voice.");
			System.exit(1);
		}

//		/* Allocates the resources for the voice.
//		 */
//		helloVoice.allocate();
//
//		/* Synthesize speech.
//		 */
//		helloVoice.speak("Thank you for giving me a voice. "
//				+ "I'm so glad to say hello to this world.");
//
//		/* Clean up and leave.
//		 */
//		helloVoice.deallocate();
		System.exit(0);
	}
	
	public static void listAllVoices() {
        System.out.println();
        System.out.println("All voices available:");        
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();
        for (int i = 0; i < voices.length; i++) {
            System.out.println("    " + voices[i].getName()
                               + " (" + voices[i].getDomain() + " domain)");
        }
    }

}
