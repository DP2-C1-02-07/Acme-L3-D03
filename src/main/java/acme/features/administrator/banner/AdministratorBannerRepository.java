
package acme.features.administrator.banner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b")
	List<Banner> findAllBanners();

	@Query("select b from Banner b where b.id = :id")
	Banner findOneBannerById(int id);

}
