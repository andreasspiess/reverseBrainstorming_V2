package com.example.reverseBrainstorming_V2.Controller;

import com.example.reverseBrainstorming_V2.Forms.NegativForm;
import com.example.reverseBrainstorming_V2.Forms.PositivForm;
import com.example.reverseBrainstorming_V2.Forms.ProblemForm;
import com.example.reverseBrainstorming_V2.RowMapper.NegativRowMapper;
import com.example.reverseBrainstorming_V2.RowMapper.PositivRowMapper;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getPositivIdeas(Model model, @RequestParam int problem_id) {

        model.addAttribute("saveNegativIdeas", new NegativForm());
        model.addAttribute("savePositivIdeas", new PositivForm());

        //System.out.println(negativForm.getProblem_id());

        // TODO aus Datenbank holen
        List<NegativForm> listNegativ = jdbcTemplate.query("SELECT * FROM negativ WHERE problem_id = ?", new NegativRowMapper(), problem_id);

        model.addAttribute("negativFormList", listNegativ);

        return "stepThree";
    }

    @PostMapping("stepThree")
    public String savePositivIdeas(Model model, PositivForm positivForm, String problem_id) {

        jdbcTemplate.update("INSERT INTO POSITIV VALUES(?, ?, ?)", positivForm.getId(), positivForm.getPositiv(), positivForm.getNegativ_id());

        //TODO aus Datenbank holen List + model + problem_id(von oben) 
        List<NegativForm> listNegativ = jdbcTemplate.query("SELECT * FROM negativ WHERE problem_id = ?", new NegativRowMapper(), problem_id);
        model.addAttribute("negativFormList", listNegativ);

        model.addAttribute("savePositivIdeas", new PositivForm());
        model.addAttribute("negativFormList", listNegativ);

        //model.addAttribute("PositivFormList", positivFormList);

        return "stepThree";
    }

    @GetMapping("stepFour")
    public String output (Model model, PositivForm positivForm, NegativForm negativForm, String problem_id) {
        model.addAttribute("problem", problem.toString());

        List<NegativForm> listNegativ = jdbcTemplate.query("SELECT * FROM negativ", new NegativRowMapper());
        List<PositivForm> listPositiv = jdbcTemplate.query("SELECT * FROM positiv", new PositivRowMapper());

        model.addAttribute("negativFormList", listNegativ);
        model.addAttribute("positivFormList", listPositiv);

        return"stepFour";
    }
}