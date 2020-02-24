package org.example.restservise;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("person")
public class PersonService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPerson() {
        return PersonRepository.getPersons();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonById(@PathParam("id") int id) {
        return PersonRepository.getPerson(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Person addPerson(String name) {
        return PersonRepository.addPerson(name);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person updatePerson(Person person) {
        return PersonRepository.updatePerson(person);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePersonById(@PathParam("id") int id) {
        return PersonRepository.deletePersonById(id) ? "OK" : "ERROR";
    }
}

