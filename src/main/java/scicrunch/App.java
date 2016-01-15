package scicrunch;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.vocab.OWLFacet.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

    public static void main( String[] args )
    {
        App app = new App();
        String[] files_ = {
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Annotation-Standard.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Cell.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Chemical.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Dysfunction-DOID-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-GO-CC-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-GrossAnatomy-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-GrossAnatomy.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-PRO-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-Role-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-Role-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITax-Bridge.owl", // validate vs slim
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITax-Slim-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-EnglishNames.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-Slim-EnglishNames.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-Slim.owl", // junk @ end of file
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-BR-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-BrainRegion-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Circuit-Role-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Circuit-Role-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-MolecularConstituent-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Morphology-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Morphology-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-NT-Bridge-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-NT-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-Quality-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Neuron-SomaLocation-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Organism.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-PRO-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Quality.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Resource.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Subcell-GO-CC-Equiv-Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Subcellular.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Unclassified.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF_Neuron_MolecularConstituent_Bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/PRO.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/SAO-CORE_properties.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/biological_process.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/cellular_component.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/quality.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/quality_bfo_bridge.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/sequence.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/sequence_slim.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/uberon-bridge-to-nifstd.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/uberon.owl",
        };
        String[] files = {
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Dysfunction-DOID-Bridge.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-PRO-Bridge.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-Role-Bridge.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Molecule-Role-Inferred.owl",
            "/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITax-Bridge.owl", // XXX not imported anywhere
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITax-Slim-Inferred.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-EnglishNames.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-Slim-EnglishNames.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-NCBITaxonomy-Slim.owl",
            //"/home/tom/git/NIF-Ontology/BiomaterialEntities/NIF-Organism.owl",
            };

        for (String file : files_) {
            try {
                app.loadSave(file);
            } catch (Exception bob) {
                System.out.println( "Failed to load file. " + file);
                System.out.println( bob.getMessage() );
            }
            System.out.println( "Hello World!" );
        }
    }
}
