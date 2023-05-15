package io.lightfeather.springtemplate;

import io.lightfeather.springtemplate.dto.Response;
import io.lightfeather.springtemplate.dto.Submit;
import io.lightfeather.springtemplate.dto.Supervisor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class ManagementController {
    @GetMapping("/supervisors")
    public List<String> getSupervisors(){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
        ResponseEntity<Supervisor[]> response=restTemplate.exchange(
                "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers",
                HttpMethod.GET,
                entity,
                Supervisor[].class
        );
        Supervisor[] data=response.getBody();
        List<String> filteredData=Arrays.stream(data)
                        .filter(s->!s.getJurisdiction().matches("-?\\d+(\\.\\d+)?"))
                        .sorted(Comparator.comparing(Supervisor::getJurisdiction).thenComparing(Supervisor::getLastName).thenComparing(Supervisor::getFirstName))
                        .map(Object::toString)
                                .collect(Collectors.toList());

        return filteredData;
    }

    @PostMapping("submit")
    public Response submit(@RequestBody Submit submit){
        if(submit.getLastName()==null||submit.getFirstName()==null||submit.getSupervisor()==null||submit.getLastName()==""||submit.getFirstName()==""||submit.getSupervisor()==""){
            return new Response(400,"there is empty field");
        }
        if(submit.getLastName().matches(".*\\d.*")||submit.getFirstName().matches(".*\\d.*")){
            return new Response(400,"name field cannot contain the number");
        }
        if(submit.getEmail()!=null||submit.getEmail()!=""){
            if(!submit.getEmail().matches("^(.+)@(.+)$")){
                return new Response(400,"please put right email format");
            }
        }
        if(submit.getPhoneNumber()!=null||submit.getPhoneNumber()!=""){
            if(!submit.getPhoneNumber().matches("^\\d{10}$")){
                return new Response(400,"please put right phone format XXXXXXXXXX");
            }
        }
        System.out.println(submit.toString());
        return new Response(200,"add user");
    }
}
