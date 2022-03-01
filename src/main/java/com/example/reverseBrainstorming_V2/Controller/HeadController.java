package com.example.reverseBrainstorming_V2.Controller;

import com.example.reverseBrainstorming_V2.Forms.NegativForm;
import com.example.reverseBrainstorming_V2.Forms.PositivForm;
import com.example.reverseBrainstorming_V2.Forms.ProblemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HeadController {

    @Bean
    public FlywayMigrationStrategy repairFlyway() {
        return flyway -> {
            // repair each script's checksum
            flyway.repair();
            // before new migrations are executed
            flyway.migrate();
        };
    }

    SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public HeadController(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("problem").usingGeneratedKeyColumns("id");
    }

    public Integer saveProblemToDBandReturnID (ProblemForm problemForm) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("problem", problemForm.getProblem());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return (Integer) newId;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final List<NegativForm> negativFormList = new ArrayList<>();
    private static final List<PositivForm> positivFormList = new ArrayList<>();
    private static String problem;

    @GetMapping("/")
    public String getStartPage(Model model) {

        model.addAttribute("saveProblem", new ProblemForm());

        return "stepOne";
    }

    @PostMapping("/")
    public String saveProblem(Model model, ProblemForm problemForm) {

        //jdbcTemplate.update("INSERT INTO PROBLEM(problem) VALUES(?)", problemForm.getProblem());

        Integer saveId = this.saveProblemToDBandReturnID(problemForm);
        NegativForm negativForm = new NegativForm();
        negativForm.setProblem_id(saveId.intValue());

        model.addAttribute("saveNegativIdeas", negativForm);
        problem = problemForm.getProblem();
        System.out.println(problem);
        model.addAttribute("problem", problem);

        return "stepTwo";
    }

    @GetMapping("stepTwo")
    public String getNegativIdeas (Model model, NegativForm negativForm) {

        model.addAttribute("saveNegativIdeas", new NegativForm());

        return "stepTwo";
    }

    @PostMapping("stepTwo")
    public String saveNegativIdeas (Model model, NegativForm negativForm) {

        jdbcTemplate.update("INSERT INTO NEGATIV VALUES(?, ?, ?)", negativForm.getId(), negativForm.getNegativ(), negativForm.getProblem_id());

        NegativForm newNegativForm = new NegativForm();
        newNegativForm.setProblem_id(negativForm.getProblem_id());

        model.addAttribute("saveNegativIdeas", newNegativForm);
        model.addAttribute("problem", problem.toString());
        negativFormList.add(negativForm);

        return "stepTwo";
    }

    @GetMapping("stepThree")
    public String getPositivIdeas(Model model, PositivForm positivForm) {
        model.addAttribute("saveNegativIdeas", new NegativForm());
        model.addAttribute("savePositivIdeas", new PositivForm());
        model.addAttribute("negativFormList", negativFormList);

        return "stepThree";
    }

    @PostMapping("stepThree")
    public String savePositivIdeas(Model model, PositivForm positivForm, NegativForm negativForm) {

        jdbcTemplate.update("INSERT INTO POSITIV VALUES(?, ?, ?)", positivForm.getId(), positivForm.getPositiv(), positivForm.getNegativ_id());

        model.addAttribute("savePositivIdeas", new PositivForm());
        model.addAttribute("negativFormList", negativFormList);

//        Integer saveId = this.saveProblemToDBandReturnID(ProblemForm);
//        PositivForm newPositivForm = new PositivForm();
//        newPositivForm.setNegativ_id(positivForm.getNegativ_id());

        positivFormList.add(positivForm);
        model.addAttribute("PositivFormList", positivFormList);

        return "stepThree";
    }

    @GetMapping("stepFour")
    public String output (Model model, PositivForm positivForm, NegativForm negativForm) {
        model.addAttribute("negativFormList", negativFormList);
        model.addAttribute("positivFormList", positivFormList);
        model.addAttribute("savePositivIdeas", new PositivForm());

        return"stepFour";
    }
}