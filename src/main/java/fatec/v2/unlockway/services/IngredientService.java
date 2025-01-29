package fatec.v2.unlockway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IIngredientService;
import fatec.v2.unlockway.domain.entity.IngredientModel;
import fatec.v2.unlockway.domain.repositories.IngredientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService implements IIngredientService {

    private final IngredientRepository repository;

    @Override
    public List<IngredientModel> findAll(){
        return repository.findAll();
    }

    @Override
    public List<IngredientModel> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public IngredientModel findById(UUID id) throws ResourceNotFoundException {
        return repository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Ingrediente não encontrado!"));
    }

    @Override
    public IngredientModel createFood(IngredientModel food) {
        return repository.save(food);
    }

}
