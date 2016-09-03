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
import org.semanticweb.owlapi.formats.OBODocumentFormat;
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
        OWLOntologyLoaderConfiguration loadConfig = new OWLOntologyLoaderConfiguration();
        loadConfig = loadConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        System.out.println(pathname);
        File infile = new File(pathname);
        File outfile = new File(pathname + ".ttl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new FileDocumentSource(infile), loadConfig);
        //ontology.getImports();

        OWLDocumentFormat owlformat = manager.getOntologyFormat(ontology);

        TurtleDocumentFormat format = new TurtleDocumentFormat();

        if (owlformat.isPrefixOWLOntologyFormat() == true) {  // obo does not support prefixes
            PrefixDocumentFormat newPrefixes = (PrefixDocumentFormat) owlformat;
            format.copyPrefixesFrom(newPrefixes);
        }
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
        for (String file: args) {
            System.out.println( "READING: " + file );
            try {
                app.loadSave(file);
            } catch (Exception bob) {
                System.out.println( "Failed to load file. " + file);
                System.out.println( bob.getMessage() );
            }
        }
    }
}
