package com.podcase.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1)
	private Long id;

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	@Column(unique=true)
	private String name;
	
	@NotBlank
	@Field
	private String password;
	
	private String imageUrl;
	
	@ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @CollectionTable(name = "preference",
        joinColumns = @JoinColumn(name = "user_id"))
    private Map<String, String> preferences;
	
//	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Set<Podcast> subscriptions = new HashSet<>();
	
//	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Set<Podcast> favourites = new HashSet<>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Podcast> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Podcast> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Map<String, String> getPreferences() {
		return preferences;
	}

	public void setPreferences(Map<String, String> preferences) {
		this.preferences = preferences;
	}

	public Set<Podcast> getFavourites() {
		return favourites;
	}

	public void setFavourites(Set<Podcast> favourites) {
		this.favourites = favourites;
	}
	
	public void addSubscription(Podcast podcast) {
		this.subscriptions.add(podcast);
	}
	
	public void removeSubscription(Podcast podcast) {
		this.subscriptions.remove(podcast);
	}
	
	public void addFavourite(Podcast podcast) { 
		this.favourites.add(podcast);
	}
	
	public void removeFavourite(Podcast podcast) {
		this.favourites.remove(podcast);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
