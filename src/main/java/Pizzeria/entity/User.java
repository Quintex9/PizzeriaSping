package Pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Email je povinný")
    @Email(message = "Email musí mať platný formát")
    @Size(max = 100, message = "Email môže mať maximálne 100 znakov")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Heslo je povinné")
    @Size(min = 8, message = "Heslo musí mať minimálne 8 znakov")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$", message = "Heslo musí obsahovať aspoň jedno písmeno a jedno číslo")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "Meno a priezvisko je povinné")
    @Size(max = 100, message = "Meno a priezvisko môže mať maximálne 100 znakov")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Pattern(regexp = "^(|0[0-9]{3} [0-9]{3} [0-9]{3})$", 
             message = "Telefón musí byť v formáte: 0944 121 302 (0XXX XXX XXX)")
    @Size(max = 20, message = "Telefón môže mať maximálne 20 znakov")
    @Column(length = 20)
    private String phone;

    @Size(max = 255, message = "Adresa môže mať maximálne 255 znakov")
    @Column(length = 255)
    private String address;

    @Size(max = 255, message = "URL obrázka môže mať maximálne 255 znakov")
    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // M:N cez user_roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleName) {
        if (roles == null || roleName == null) {
            return false;
        }

        return roles.stream()
                .anyMatch(role -> role.getName().equals("ROLE_" + roleName));
    }

}

