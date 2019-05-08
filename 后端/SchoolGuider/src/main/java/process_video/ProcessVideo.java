package process_video;

import com.google.gson.Gson;
import tool.PingAnTool;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessVideo {
    String[] types = { "Optimism","Joy","Desire","Vitality","Sincerity","Apprehension","Harmony","Submission","Fatigue","Annoyance","Depression","Interest","Calm","Acceptance","Cowardice","Surprise","Serenity","Boredom","Grievance","Fear","Pessimism","Tension","Pride","Puzzlement","Passiveness","Distraction","Embarrassed","Anticipation","Suspicion","Neutral","Admiration","Awe","Contempt","Bravery","Boastfulness","Envy","Trust","Aggressiveness","Disgust","Uneasiness","Angry","Deceptiveness","Conflict","Sadness","Hate","Love","Neglect","Disapproval","Remorse","Insincerity","Shame","Gratitude","Defiance","Insult" };

    String resFilePath = "res.csv";

    void getType() {
        String testMsg = "[{\"confid\":0.385,\"type\":\"Optimism\"},{\"confid\":0.271,\"type\":\"Joy\"},{\"confid\":0.124,\"type\":\"Desire\"},{\"confid\":0.097,\"type\":\"Vitality\"},{\"confid\":0.029,\"type\":\"Sincerity\"},{\"confid\":0.026,\"type\":\"Apprehension\"},{\"confid\":0.015,\"type\":\"Harmony\"},{\"confid\":0.009,\"type\":\"Submission\"},{\"confid\":0.007,\"type\":\"Fatigue\"},{\"confid\":0.007,\"type\":\"Annoyance\"},{\"confid\":0.006,\"type\":\"Depression\"},{\"confid\":0.005,\"type\":\"Interest\"},{\"confid\":0.005,\"type\":\"Calm\"},{\"confid\":0.004,\"type\":\"Acceptance\"},{\"confid\":0.002,\"type\":\"Cowardice\"},{\"confid\":0.002,\"type\":\"Surprise\"},{\"confid\":0.001,\"type\":\"Serenity\"},{\"confid\":0.001,\"type\":\"Boredom\"},{\"confid\":0.001,\"type\":\"Grievance\"},{\"confid\":0.001,\"type\":\"Fear\"},{\"confid\":0.0,\"type\":\"Pessimism\"},{\"confid\":0.0,\"type\":\"Tension\"},{\"confid\":0.0,\"type\":\"Pride\"},{\"confid\":0.0,\"type\":\"Puzzlement\"},{\"confid\":0.0,\"type\":\"Passiveness\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Embarrassed\"},{\"confid\":0.0,\"type\":\"Anticipation\"},{\"confid\":0.0,\"type\":\"Suspicion\"},{\"confid\":0.0,\"type\":\"Neutral\"},{\"confid\":0.0,\"type\":\"Admiration\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Boastfulness\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Trust\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Disgust\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Angry\"},{\"confid\":0.0,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Shame\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Insult\"}]";
        testMsg = testMsg.substring( 1, testMsg.length()-1 );
        Pattern pattern = Pattern.compile( "(\\{\"confid\":\\d*\\.\\d*,\"type\":\"(.*?)\"\\})*" );
        List<String> types = new ArrayList<>();
        while( true ) {
            Matcher matcher = pattern.matcher( testMsg );
            if( matcher.find() ) {
                types.add( matcher.group(2) );
                try {
                    testMsg = testMsg.substring( matcher.group(1).length()+1 );
                } catch ( Exception e ) {
                    break;
                }
            } else {
                break;
            }
        }
        for( String type : types ) {
            System.out.print( "\"" );
            System.out.print( type );
            System.out.print( "\"" );
            System.out.print( "," );
        }
    }

    void solve( String filePath, int frame_index ) throws Exception {
        File file = new File( resFilePath );
        PrintWriter printWriter = new PrintWriter( new FileWriter( file, true ));

        String testMsg = new PingAnTool().getEmotionMessage( filePath );
        if( testMsg.equals( "fail" ) ) {
            return;
        }
        System.out.println( testMsg );
        printWriter.write( String.valueOf(frame_index) + "," );
        List list = new Gson().fromJson( testMsg, List.class );
        for( int i = 0; i < list.size(); ++i ) {
            Bean bean = new Gson().fromJson( list.get(i).toString(), Bean.class );
            printWriter.write( bean.confid.toString() );
            printWriter.write( "," );
        }
        printWriter.write( "\n" );
        printWriter.close();
    }

    public static void main( String[] args ) throws Exception {
        ProcessVideo processVideo = new ProcessVideo();
        String basePath = "E:\\Java\\IDEA_Personal_File\\DataHacker\\frames\\img_?.jpg";
        for( int i = 0; i <= 1576; ++i ) {
            System.out.println( i );
            String filePath = basePath.replace( "?", String.valueOf(i) );
            processVideo.solve( filePath, i );
        }
    }
}

class Bean {
    String type;
    Double confid;
}
