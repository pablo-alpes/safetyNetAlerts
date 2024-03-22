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
    * 
  * [Response Format](#response-format)
  * [Error Handling](#error-handling)
  * [Getting Help](#getting-help)
<!-- TOC -->

## Base URL
https://api.safetynetalerts.com:8080
### Authentication
* Authorization: Bearer your_api_token 
* The Safety Net Alerts API uses token-based authentication. To authenticate your requests, include your API token in the
Authorization header of your HTTP requests.

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

## CRUD (Update, Delete, Add)
Under development

## Response Format
The API returns data in JSON format. Each endpoint's response is documented in the respective endpoint description.

## Error Handling
The API uses standard HTTP status codes to indicate the success or failure of a request. In case of an error, additional
information may be provided in the response body.

## Getting Help
If you have any questions, concerns, or need assistance, please contact our support team at support@safetynetalerts.com.

Thank you for using the Safety Net Alerts API!