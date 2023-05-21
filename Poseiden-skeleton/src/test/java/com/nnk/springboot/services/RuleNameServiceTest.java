package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.impl.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class RuleNameServiceTest {
    private IRuleNameService ruleNameService;
    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setup() {
        ruleNameService = new RuleNameService(ruleNameRepository);
    }

    @DisplayName(value = "1°) Recherche de tous les RuleName")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_RuleName() {
        List<RuleName> ruleNameList = new ArrayList<>();

        RuleName ruleName1 = new RuleName();
        RuleName ruleName2 = new RuleName();
        RuleName ruleName3 = new RuleName();

        ruleNameList.add(ruleName1);
        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);

        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        List<RuleName> result = ruleNameService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(ruleNameList.size());
    }

    @DisplayName(value = "2°) Recherche de RuleName par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findRuleName_By_Id() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

        RuleName ruleNameResult = ruleNameService.findById(ruleName.getId());

        assertThat(ruleNameResult).isNotNull();
        assertThat(ruleNameResult.getId()).isEqualTo(ruleName.getId());
    }

    @DisplayName(value = "3°) Mise à jour d'un RuleName Existant")
    @Order(3)
    @Test
    void test_update_should_update_RuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        RuleName ruleNameUpdated = new RuleName();
        ruleNameUpdated.setId(1);
        ruleNameUpdated.setName("nameTestUpdated");
        ruleNameUpdated.setDescription("descriptionTestUpdated");
        ruleNameUpdated.setJson("{ \"message\" : \"message breaks json Updated\" }");
        ruleNameUpdated.setTemplate("templateTestUpdated");
        ruleNameUpdated.setSqlStr("sqlStrTestUpdated");
        ruleNameUpdated.setSqlPart("sqlPartTestUpdated");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleNameUpdated);

        RuleName ruleNameResult = ruleNameService.save(ruleName);

        assertThat(ruleNameResult).isNotNull();
        assertThat(ruleNameResult.getId()).isEqualTo(ruleNameUpdated.getId());
        assertThat(ruleNameResult.getName()).isEqualTo(ruleNameUpdated.getName());
        assertThat(ruleNameResult.getDescription()).isEqualTo(ruleNameUpdated.getDescription());
        assertThat(ruleNameResult.getJson()).isEqualTo(ruleNameUpdated.getJson());
        assertThat(ruleNameResult.getTemplate()).isEqualTo(ruleNameUpdated.getTemplate());
        assertThat(ruleNameResult.getSqlStr()).isEqualTo(ruleNameUpdated.getSqlStr());
        assertThat(ruleNameResult.getSqlPart()).isEqualTo(ruleNameUpdated.getSqlPart());
    }

    @DisplayName(value = "4°) Suppression de RuleName par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteRuleName_By_Id() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

        boolean result = ruleNameService.deleteById(ruleName.getId());

        assertThat(result).isTrue();
    }
}
