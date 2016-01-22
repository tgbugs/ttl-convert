package scicrunch;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.vocab.OWLFacet.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.*; // ontology manager lives here

import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.FileDocumentTarget;

/**
 * ttl-convert
 *
 */
public class App 
{
    public void loadSave( String pathname ) throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        System.out.println(pathname);
        File infile = new File(pathname);
        File outfile = new File(pathname + ".ttl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new FileDocumentSource(infile));
        //ontology.getImports();

        OWLDocumentFormat owlformat = manager.getOntologyFormat(ontology);
        PrefixDocumentFormat newPrefixes = (PrefixDocumentFormat) owlformat;

        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.copyPrefixesFrom(newPrefixes);
        //manager.setOntologyFormat(ontology, format);
        //BUG hasDbXref structure fails to translate using owlapi 4.1.1 but works on 4.0.2
        manager.saveOntology(ontology, format, new FileDocumentTarget(outfile));
    }

    public String[] get_file_list( String pathname ) throws Exception {
        FileReader fileReader = new FileReader(pathname);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static void main( String[] args )
    {
        App app = new App();
        for (String file_list: args) {
            try {
                String[] files = app.get_file_list(file_list);
                for (String file : files) {
                    try {
                        app.loadSave(file);
                    } catch (Exception bob) {
                        System.out.println( "Failed to load file. " + file);
                        System.out.println( bob.getMessage() );
                    }
                    //System.out.println( "Hello World!" );
                }
            } catch (Exception phil) {
                    System.out.println( "Failed to find file list. " + file_list);
                    System.out.println( phil.getMessage() );
            }

        }
    }
}