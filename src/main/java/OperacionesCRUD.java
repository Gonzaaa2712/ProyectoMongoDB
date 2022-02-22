
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.Scanner;

/**
 * Clase creada para realizar las operaciones CRUD contra una BD NoSQL MongoDB
 */
public class OperacionesCRUD {

    private static MongoClient cliente;
    private static MongoDatabase bd;
    private static MongoCollection<Document> coleccion;


    public static void main(String[] args) {


            // Métodos para establecer la conexión con la BD.
            cliente = MongoClients.create();
            bd = cliente.getDatabase("adat");
            coleccion = bd.getCollection("alumnos");

            // Algunas llamadas a los métodos como prueba, pueden ser remplazados por los valores que se quiera.

            //listarTodosAlumnos();
            //insertarAlumno();
            //listarTodosAlumnos();
            //borrarAlumno("Roberto", "Morales");
            //listarTodosAlumnos();
            //borrarAlumno("Cristiano", "Ronaldo");
            //modificarAlumno("Gonzalo", "Ramiro");
            //listarPorCurso("DAM1");

    }


    /**
     * Función que lista todos los documentos de una colección.
     */
    public static void listarTodosAlumnos(){

        FindIterable<Document> listadoAlumnos = coleccion.find();

        for (Document alumno : listadoAlumnos) {
            System.err.println("-" + alumno.get("nombre") + " " + alumno.get("apellido1")+ " " + alumno.get("apellido2"));
        }

    }

    /**
     * Función que busca a todos los alumnos filtrando por curso.
     * @param curso
     */
    public static void listarPorCurso(String curso){

        // Filtrando aquellos alumnos cuyo curso sea DAM1

        FindIterable<Document> documents = coleccion.find(Filters.eq("curso",curso));
        for (Document alumno : documents) {
            System.err.println("-" + alumno.get("nombre") + " " + alumno.get("apellido1")+ " " + alumno.get("apellido2"));
        }

    }

    /**
     * Función que inserta un alumno nuevo en la colección
     */
    public static void insertarAlumno(){

        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduce el nombre del alumno: ");
        String nombre = entrada.nextLine();
        System.out.println("Introduce el primer apellido del alumno: ");
        String apellido1 = entrada.nextLine();
        System.out.println("Introduce el segundo apellido del alumno: ");
        String apellido2 = entrada.nextLine();
        System.out.println("Introduce el curso del alumno: ");
        String curso = entrada.nextLine();

        // Nos creamos un nuevo Documento y le hacemos append los datos introducidos por teclado

        Document document = new Document("nombre", nombre)
                .append("apellido1", apellido1)
                .append("apellido2", apellido2)
                .append("curso", curso);

        coleccion.insertOne(document);

    }


    /**
     * Función que borra un alumno de la colección.
     * @param nombre
     * @param apellido1
     */
    public static void borrarAlumno(String nombre, String apellido1){

        // Borrando a un alumno de la BD

        DeleteResult borrado = coleccion.deleteOne(Filters.and(Filters.eq("nombre",nombre), Filters.eq("apellido1",apellido1)));
        if(borrado.getDeletedCount() == 1){
            System.err.println("El alumno " + nombre + " " + apellido1 + " ha sido borrado correctamente de la BD.");
        } else {
            System.err.println(nombre + " " + apellido1 + " no existe en la BD.");
        }
    }


    /**
     * Función que modifica un alumno poniendo como curso DAM1.
     * @param nombre
     * @param apellido1
     */
    public static void modificarAlumno(String nombre, String apellido1){

        FindIterable<Document> alumnoGonzaloRamiro = coleccion.find(Filters.and(Filters.eq("nombre",nombre), Filters.eq("apellido1",apellido1)));
        Document gonzaloRamiroEditado = new Document("name", "Gonzalo")
                .append("apellido1", "Ramiro")
                .append("apellido2", "Muñoz")
                .append("curso", "DAM1");

        // Hacemos el update del alumno.

        coleccion.updateOne(Filters.and(Filters.eq("nombre",nombre), Filters.eq("apellido1",apellido1)), new Document("$set", new Document("curso", "DAM1")));


    }



}
