package fatec.v2.unlockway.application;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fatec.v2.unlockway.api.dto.ingredients.CreateIngredientDTO;
import fatec.v2.unlockway.api.dto.ingredients.GetIngredientDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.entity.IngredientModel;
import fatec.v2.unlockway.services.IngredientService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/ingredients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class IngredientController {

    private final ModelMapper modelMapper;
    private final IngredientService service;
    
    @GetMapping("")
    public ResponseEntity<List<GetIngredientDTO>> findAll(){
        List<GetIngredientDTO> ingredients = service.findAll()
            .stream()
            .map(ingredient -> modelMapper.map(ingredient, GetIngredientDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        try {
            IngredientModel ingredient = service.findById(id);
            GetIngredientDTO ingredientDto = modelMapper.map(ingredient, GetIngredientDTO.class);

            return ResponseEntity.status(HttpStatus.OK).body(ingredientDto);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());   
        }
    }

    @PostMapping("")
    public ResponseEntity<GetIngredientDTO> createFood(@RequestBody CreateIngredientDTO createFoodDTO) {
        IngredientModel ingredientModel = modelMapper.map(createFoodDTO, IngredientModel.class);

        IngredientModel createdIngredient = service.createFood(ingredientModel);

        GetIngredientDTO ingredientDTO = modelMapper.map(createdIngredient, GetIngredientDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientDTO);

    }

    @GetMapping("/findByName")
    public  ResponseEntity<List<IngredientModel>> findByName(@RequestParam("name") String name){
        List<IngredientModel> ingredientModels = service.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientModels);
    }
}
