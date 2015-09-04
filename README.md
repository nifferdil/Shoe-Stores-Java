# _Shoe Stores_

##### _Create a shoe store brand locator. Date of current version: 09/04/15_

#### By _**Jennifer Morkunas**_

## Description

Add brands to shoe stores and shoe stores to brands.

## Setup

* Clone or fork from github
* Adjust settings in DB.java to work with your db setup
* Create a database shoe_stores
* Run on gradle or on a server
* Open in your browser!

In PSQL:

CREATE DATABASE shoe_stores;
\c shoe_stores
CREATE TABLE brands (id serial PRIMARY KEY, brand_name varchar);
CREATE TABLE stores (id serial PRIMARY KEY, name varchar);
CREATE TABLE stores_brands (id serial PRIMARY KEY, store_id int, brand_id int);
CREATE DATABASE shoe_stores_test WITH TEMPLATE shoe_stores;

## Technologies Used

Java, Velocity Templating, Postgres Database, HTML and CSS



### Legal

Copyright (c) 2015 **Jennifer Morkunas**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
