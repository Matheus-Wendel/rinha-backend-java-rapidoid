package com.matheus.app;

import java.util.UUID;

import org.rapidoid.annotation.Valid;
import org.rapidoid.http.Req;
import org.rapidoid.jpa.JPA;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.matheus.app.domain.Pessoa;

public class Server {

    private static final String consulta_parametro = "FROM Pessoa p WHERE (p.nome LIKE ?1) or (p.apelido LIKE ?1) or (p.stack LIKE ?1)";

    public void configureServer() {

        String[] s = { "com.matheus.app" };
        JPA.bootstrap(s, Pessoa.class);

        On.post("/pessoas").json((Req req, @Valid Pessoa p) -> cadastrarPessoa(req, p));
        On.get("/pessoas").json((Req req, String t) -> consultaPessoaPorParametro(req));
        On.get("/pessoas/{id}").json((String id) -> consultaId(id));
        On.get("/contagem-pessoas").json((Req req) -> contagemPessoas());

        My.errorHandler((req, resp, error) -> {
            return resp.code(422).result("Error: " + error.getMessage());

        });
        // On.post("/hello").plain((Req req, Resp resp, Pessoa p) -> {

        // req.async(); // mark asynchronous request processing

        // // send part 1
        // // resp.chunk("part 1".getBytes());

        // resp.json(p);
        // resp.done();
        // // after some time, send part 2 and finish
        // Jobs.after(1000).milliseconds(() -> {
        // System.out.println("oi");
        // });
        // return resp;

        // });

    }

    private Object contagemPessoas() {

        Long count = JPA.count(Pessoa.class);
        return count;
    }

    private Pessoa consultaId(String id) {

        UUID uuid = UUID.fromString(id);
        return JPA.get(Pessoa.class, uuid);
    }

    private Object cadastrarPessoa(Req req, Pessoa p) {

        // System.out.println("pessoa");
        // System.out.println(p);
        // System.out.println("data");

        // System.out.println(req.data());
        p.id = UUID.randomUUID();
        if (!p.validate()) {
            return req.response().result("e").code(400);
        }
        Pessoa save = JPA.save(p);
        req.response().json(save).code(201);
        return p;
    }

    private Object consultaPessoaPorParametro(Req req) {
        try {
            String t = req.param("t");

            // System.err.println(t);

            String param = "%" + t + "%";
            return JPA.jpql(consulta_parametro, param).all();
        } catch (Exception e) {
            System.err.println(e);
            return req.response().result("e").code(400);

        }

    }
}
