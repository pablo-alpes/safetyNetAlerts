### Safety Net Alerts API Documentation
* Date: 14/03/2024
* Author: Pablo Miranda

Welcome to the Safety Net Alerts API documentation. This document provides an overview of the available endpoints,
request and response formats, authentication requirements, and other important details for interacting with the Safety
Net Alerts API.

<!-- TOC -->
### Table of Contents
  * [Base URL](#base-url)
    * [Authentication](#authentication)
  * [Available endpoints](#available-endpoints)
  * [GET](#get)
    * [Person Information](#person-information)
    * [Community Email List](#community-email-list)
    * [Child Alert](#child-alert)
    * [Firestation Information](#firestation-information)
    * [Flood Information](#flood-information)
    * [Phone Alert](#phone-alert)
  * [CRUD (Update, Delete, Add)](#crud-update-delete-add)
  * [Response Format](#response-format)
  * [Error Handling](#error-handling)
  * [Getting Help](#getting-help)
<!-- TOC -->

## Base URL
To use the API you can use it whether by any client or by the UI.

### Authentication
No security credentials are needed. All users with the link will have access.

## Available endpoints
## GET
### Person Information

* Endpoint: /api/personInfo?firstName=string&lastName=string

* Method: GET
* Description: Retrieves information for an individual based on the provided first name and last name.
* Parameters:
  * firstName (string): The first name of the individual. 
  * lastName (string): The last name of the individual. 
* Response: JSON object containing the person's information.

### Community Email List
* Endpoint: /api/communityEmail
* Method: GET
* Description: Retrieves the email list for all individuals in a specific city.
* Parameters:
  * city (string): The name of the city.
* Response: JSON array containing the email addresses.

### Child Alert
* Endpoint: /api/childAlert
* Method: GET
* Description: Retrieves information for children living at a specific address, along with their family members.
* Parameters:
  * address (string): The address for which to retrieve child and family information.
* Response: JSON object containing the children and family members' information.

### Firestation Information
* Endpoint: /api/firestation
* Method: GET
* Description: Retrieves information about the firestations serving a specific address.
* Parameters:
  * address (string): The address for which to retrieve firestation information.
* Response: JSON object containing the firestation details.

### Flood Information
* Endpoint: /api/flood
* Method: GET
* Description: Retrieves people grouped by their firestation numbers.
* Parameters:
  * stations (array of integers): The firestation numbers for which to retrieve people information.
* Response: JSON object containing the people grouped by firestation.

### Phone Alert
* Endpoint: /api/phoneAlert
* Method: GET
* Description: Retrieves phone numbers of people served by a specific firestation.
* Parameters:
  * station (integer): The firestation number for which to retrieve phone numbers.
* Response: JSON array containing the phone numbers.

## CRUD (Update, Delete, Add) Operations
The Safety Alerts API also supports CRUD (Create, Read, Update, Delete) operations for managing various entities such as people, medical records, and firestations.

##DELETE Operations
### /person/{firstName}&{lastName}
* Description: Delete a person's record by their first name and last name.
* Method: DELETE
* Parameters:
 * firstName (String): First name of the person to delete
 * lastName (String): Last name of the person to delete
* Action: Deletes the person's record from the database.

### /medicalRecord/{firstName}&{lastName}
Description: Delete a medical record by the person's first name and last name.
Method: DELETE
Parameters:
firstName (String): First name of the person whose medical record to delete
lastName (String): Last name of the person whose medical record to delete
Action: Deletes the medical record from the database.

### /firestation/{param}

Description: Delete a firestation record by its parameter.
Method: DELETE
Parameters:
param (String): Parameter for identifying the firestation record to delete
Action: Deletes the firestation record from the database.

## UPDATE Operations (PUT)
### /medicalRecord
Description: Update a medical record.
Method: PUT
Request Body: JSON object representing the updated medical record
Action: Updates the medical record in the database.
###/firestation
Description: Update a firestation record.
Method: PUT
Request Body: JSON object representing the updated firestation record
Action: Updates the firestation record in the database.

### /person

Description: Update a person's record.
Method: PUT
Request Body: JSON object representing the updated person's record
Action: Updates the person's record in the database.
## ADD Operation (POST)
### /medicalRecord
* Description: Add a new medical record.
* Method: POST
* Request Body: JSON object representing the new medical record
* Action: Adds the new medical record to the database.
### /firestation

* Description: Add a new firestation record.
* Method: POST
* Request Body: JSON object representing the new firestation record
* Action: Adds the new firestation record to the database.

### /person
* Description: Add a new person's record.
* Method: POST
* Request Body: JSON object representing the new person's record
* Action: Adds the new person's record to the database.

## Response Format
The API returns data in JSON format. Each endpoint's response is documented in the respective endpoint description.

## Error Handling
The API uses standard HTTP status codes to indicate the success or failure of a request. In case of an error, additional
information may be provided in the log.

## Getting Help
If you have any questions, concerns, or need assistance, please contact our support team at support@safetynetalerts.com.

Thank you for using the Safety Net Alerts API!
