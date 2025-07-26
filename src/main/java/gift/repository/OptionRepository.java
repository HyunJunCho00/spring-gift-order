package gift.repository;

import gift.entity.Option;
import gift.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);
    Optional<Option> findByProductAndName(Product product, String name);
}