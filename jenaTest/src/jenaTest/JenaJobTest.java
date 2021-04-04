package jenaTest;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;
import java.util.Iterator;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import org.apache.jena.rdf.model.Resource;



public class JenaJobTest {

    public static final String NAMESPACE = "example.org/";
    public static final String JOBS_NAMESPACE = "http://www.semanticweb.org/pooja/ontologies/2021/1/jobs.owl#";

    public static void main(String[] args) {
       // simpleJenaReasoningExample();
        extendedJenaReasoningExample();
    }

    private static void extendedJenaReasoningExample() {
        
        Model ontology = FileManager.getInternal().loadModelInternal("/home/pooja/eclipse-workspace/jenaTest/src/jobs.owl");
       

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infOntology = ModelFactory.createInfModel(reasoner, ontology);
        Resource infDataScientist = infOntology.getResource(JOBS_NAMESPACE + "Data_Scientist");
        Resource infPD = infOntology.getResource(JOBS_NAMESPACE + "Python_Developer");
        Property infHasSchedule = infOntology.getProperty(JOBS_NAMESPACE + "hasSchedule");
        System.out.println(infDataScientist.getProperty(infHasSchedule));
        System.out.println(infPD.getProperty(infHasSchedule));
        
        String queryString=
        		"PREFIX : <http://www.semanticweb.org/pooja/ontologies/2021/1/jobs.owl#>" +
    			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
    			"PREFIX xml: <http://www.w3.org/XML/1998/namespace>" +
    			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
    			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
    			"PREFIX base: <http://www.semanticweb.org/pooja/ontologies/2021/1/jobs.owl#>" +		
    			"SELECT ?x ?y WHERE {" +
    			":FullTime rdfs:subClassOf ?x ." +
    			//":IBM :posts ?y ." +
    			//"?z rdfs:subClassOf :Education ." +
    			"}" ;
    	System.out.println();
    	
    	Query query =QueryFactory.create(queryString);
    	QueryExecution qexec = QueryExecutionFactory.create(query, ontology);
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		
    		while(results.hasNext()) {
    			QuerySolution soln = results.nextSolution();
    			//System.out.println("Hello Again!!");
    			System.out.println(soln.getResource("x"));
    			System.out.println();
    			//System.out.println(soln.getResource("y"));
    			//System.out.println(soln.getResource("z"));
    		}
    	}
    	finally {
    		qexec.close();
    	}
    }
    
    private static void simpleJenaReasoningExample() {
        // Build a trivial example data set
        Model ontology = ModelFactory.createDefaultModel();

        Resource person = ontology.createResource(NAMESPACE + "Person");
        Resource cat = ontology.createResource(NAMESPACE + "Cat");
        Property hasPet = ontology.createProperty(NAMESPACE, "has_pet");
        Property likes = ontology.createProperty(NAMESPACE, "likes");

        ontology.add(hasPet, RDFS.subPropertyOf, likes);
        person.addProperty(hasPet, cat);

       // ontology.write(System.out, "Turtle");

       System.out.println();
        System.out.println("Statement: " + person.getProperty(likes));
        System.out.println("Statement: " + person.getProperty(hasPet));
        System.out.println(); 

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infOntology = ModelFactory.createInfModel(reasoner, ontology);
      /*  ValidityReport validate = infOntology.validate();
        System.out.println("Valid?: " + (validate.isValid() ? "Yes" : "No"));
        System.out.println("Clean?: " + (validate.isClean() ? "Yes" : "No"));*/

        Resource infPerson = infOntology.getResource(NAMESPACE + "Person");
        System.out.println();
        System.out.println("Statement: " + infPerson.getProperty(likes));
        System.out.println("Statement: " + infPerson.getProperty(hasPet));
        System.out.println(); 

       // infOntology.write(System.out,"Turtle");
    }

}

