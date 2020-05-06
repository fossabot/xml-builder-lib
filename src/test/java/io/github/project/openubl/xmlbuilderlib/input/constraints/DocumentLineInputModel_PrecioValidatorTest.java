/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xmlbuilderlib.input.constraints;

import io.github.project.openubl.xmlbuilderlib.models.input.constraints.DocumentLineInputModel_PrecioValidator;
import io.github.project.openubl.xmlbuilderlib.models.input.constraints.HighLevelGroupValidation;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.DocumentLineInputModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentLineInputModel_PrecioValidatorTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void precioConIgv_isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .withPrecioConIgv(BigDecimal.ONE)
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, HighLevelGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void precioUnitario_isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .withPrecioUnitario(BigDecimal.ONE)
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, HighLevelGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void noPrecioConSinImpuestos_isInvalid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, HighLevelGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentLineInputModel_PrecioValidator.message))
        );
    }
}
