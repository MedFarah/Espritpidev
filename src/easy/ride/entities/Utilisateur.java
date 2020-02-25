package easy.ride.entities;

public class Utilisateur {

	private int id_user;
        private String role;
	private String login;
	private String password;
	private String nomcomplet;
	private String mail;
	private String tel;
	private String adresse;
	private String dateNaissance;
        private String dateCreation;

    public Utilisateur() {
    }
	
	
	
	public Utilisateur(String login, String role, String password,String nomcm, String mail, String tel, String date, String adresse) {
		this.login = login;
                this.role = role;
                this.password = password;
		this.nomcomplet = nomcm;
		this.mail = mail;
		this.tel = tel;
		this.adresse = adresse;
		this.dateNaissance = date;
	}
        
        public Utilisateur(int id,String login, String role, String password,String nomcm, String mail, String tel, String date, String adresse) {
		this.id_user = id;
                this.login = login;
                this.role = role;
                this.password = password;
		this.nomcomplet = nomcm;
		this.mail = mail;
		this.tel = tel;
		this.adresse = adresse;
		this.dateNaissance = date;
	}


	public String getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public int getId_user() {
		return id_user;
	}
        
        


	public void setId_user(int id_user) {
		this.id_user = id_user;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public String getNomcomplet() {
		return nomcomplet;
	}
        
        public String getDateCreation() {
		return dateCreation;
	}
        
         public void setDateCreation(String date) {
		this.dateCreation = date;
	}

        public String getRole() {
        return role;
        }

        public void setRole(String role) {
        this.role = role;
        }
	        
	public void setNomcomplet(String nomcomplet) {
		this.nomcomplet = nomcomplet;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


        
	 @Override
	    public String toString() {
	        return "Personne{" + "id=" + id_user + ", nom complet=" + nomcomplet + ", role=" + getRole() +", login=" + login + ", Password=" + password + ", adresse=" + adresse + ", Email=" + mail +", Telephone=" +tel +   ", date Naissance =" + dateNaissance +", Date Creation Profil=" +dateCreation +  '}';
	    }

  
  
		
	
	
	
	
	
}
