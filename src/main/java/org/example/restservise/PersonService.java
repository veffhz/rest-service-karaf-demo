package org.example.restservise;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("person")
public class PersonService {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getPerson() {
        return PersonRepository.getPersons();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Person getPersonById(@PathParam("id") int id) {
        return PersonRepository.getPerson(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.TEXT_PLAIN)
    public Person addPerson(String name) {
        return PersonRepository.addPerson(name);
    }

    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Person updatePerson(Person person) {
        return PersonRepository.updatePerson(person);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String deletePersonById(@PathParam("id") int id) {
        return PersonRepository.deletePersonById(id) ? "OK" : "ERROR";
    }
}

