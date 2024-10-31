package com.example.lab8.controller;

import com.example.lab8.entity.Estudiantes;
import com.example.lab8.repository.EstudiantesRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class EstudiantesController {

    final EstudiantesRepository estudiantesRepository;

    public EstudiantesController (EstudiantesRepository estudiantesRepository){
        this.estudiantesRepository = estudiantesRepository;
    }

    //listar
    @GetMapping(value = {"/list"})
    public List<Estudiantes> listaEstudiantes() {
        return estudiantesRepository.findAll();
    }
    @PostMapping(value = { "/new"})
    public ResponseEntity<HashMap<String, Object>> guardarEstudiante(
            @RequestBody Estudiantes estudiantes,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        estudiantesRepository.save(estudiantes);
        if (fetchId) {
            responseJson.put("id", estudiantes.getId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @PutMapping(value = { "/update"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Estudiantes estudiantesRecibido) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (estudiantesRecibido.getId() != null && estudiantesRecibido.getId() > 0) {

            Optional<Estudiantes> byId = estudiantesRepository.findById(estudiantesRecibido.getId());
            if (byId.isPresent()) {
                Estudiantes studentFromDb = byId.get();

                if (estudiantesRecibido.getNombre() != null)
                    studentFromDb.setNombre(estudiantesRecibido.getNombre());

                if (estudiantesRecibido.getGpa() != null)
                    studentFromDb.setGpa(estudiantesRecibido.getGpa());

                if (estudiantesRecibido.getFacultad() != null)
                    studentFromDb.setFacultad(estudiantesRecibido.getFacultad());

                if (estudiantesRecibido.getCreditos() != null)
                    studentFromDb.setCreditos(estudiantesRecibido.getCreditos());

                estudiantesRepository.save(studentFromDb);
                rpta.put("result", "ok");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("result", "error");
                rpta.put("msg", "El ID del producto enviado no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        } else {
            rpta.put("result", "error");
            rpta.put("msg", "debe enviar un producto con ID");
            return ResponseEntity.badRequest().body(rpta);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("id") String idStr){

        try{
            int id = Integer.parseInt(idStr);

            HashMap<String, Object> rpta = new HashMap<>();

            Optional<Estudiantes> byId = estudiantesRepository.findById(id);
            if(byId.isPresent()){
                estudiantesRepository.deleteById(id);
                rpta.put("result","ok");
            }else{
                rpta.put("result","no ok");
                rpta.put("msg","el ID enviado no existe");
            }

            return ResponseEntity.ok(rpta);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionException(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un estudiante");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
