package com.naveen.csvfile.springbootapp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "developer_tutorial")
public class DeveloperTutorial {

	  @Id
	  @Column(name = "id")
	  private long id;

	  @Column(name = "name")
	  private String name;

	  @Column(name = "description")
	  private String description;

	  @Column(name = "updatedTimestamp")
	  private Date updatedTimestamp;

	  public DeveloperTutorial() {

	  }

	  public DeveloperTutorial(long id, String name, String description, Date updatedTimestamp) {
	    this.id = id;
	    this.name = name;
	    this.description = description;
	    this.updatedTimestamp = updatedTimestamp;
	  }

		public long getId() {
			return id;
		}
	
		public void setId(long id) {
			this.id = id;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	
		public Date getUpdatedTimestamp() {
			return updatedTimestamp;
		}
	
		public void setUpdatedTimestamp(Date updatedTimestamp) {
			this.updatedTimestamp = updatedTimestamp;
		}

		@Override
		public String toString() {
			return "DeveloperTutorial [id=" + id + ", name=" + name + ", description=" + description
					+ ", updatedTimestamp=" + updatedTimestamp + "]";
		}

		
	}
