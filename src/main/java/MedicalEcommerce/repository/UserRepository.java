package MedicalEcommerce.repository;

import MedicalEcommerce.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserDtls,Integer>{

    public UserDtls findByEmail(String email);

    @Query ("Select c from UserDtls c where role='ROLE_CUSTOMER'")
    public List<UserDtls> findAllCustomer();

    @Query ("Select s from UserDtls s where role='ROLE_SELLER'")
    public List<UserDtls> findAllSeller();

    public Optional<UserDtls> findById(int id);

    public void deleteById(int id);

    @Query("Select s from UserDtls s where email =?1")
    UserDtls getuserid(String seller);

}