package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.entity.IngredientModel;

import java.util.List;
import java.util.UUID;

public interface IIngredientService {
  List<IngredientModel> findAll();
  List<IngredientModel> findByName(String name);
  IngredientModel findById(UUID id) throws ResourceNotFoundException;
  IngredientModel createFood(IngredientModel ingredient);
}