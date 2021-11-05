/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  10337
 * Created: 2021年11月4日
 */


CREATE TABLE Users (
    UserID varchar(30),
    Yelp_since date NOT NULL,
    UName varchar(40) NOT NULL,
    funny int DEFAULT 0,
    cool int DEFAULT 0,
    useful int DEFAULT 0,
    votes int DEFAULT 0,
    review_count int DEFAULT 0,
    average_stars float DEFAULT 0,
    friends_num int DEFAULT 0,
    PRIMARY KEY (UserID)
);

CREATE INDEX INDEX1 ON Users (Yelp_since);
CREATE INDEX INDEX2 ON Users (Votes);
CREATE INDEX INDEX11 ON Users (Friends_num);
CREATE INDEX INDEX12 ON Users (average_stars);
CREATE INDEX INDEX13 ON Users (review_count);

CREATE TABLE Business (
    BusinessID VARCHAR(30),
    BusinessName VARCHAR(100) NOT NULL,
    City VARCHAR(30) NOT NULL,
    BState VARCHAR(10) NOT NULL,
    Stars float DEFAULT 0, 
    PRIMARY KEY (BusinessID)
);

CREATE Table Business_Categories (
    BusinessID VARCHAR(30),
    CName VARCHAR(30),
    PRIMARY KEY(BusinessID, CName),
    FOREIGN KEY (BusinessID) REFERENCES Business(BusinessID) ON DELETE CASCADE
);

CREATE INDEX INDEX5 ON Business_Categories (CName);
CREATE INDEX INDEX6 ON Business_Categories (BusinessID);

CREATE Table Business_Subcategories(
    BusinessID VARCHAR(30),
    SubcName VARCHAR(40),
    PRIMARY KEY(BusinessID, SubcName),
    FOREIGN KEY (BusinessID) REFERENCES Business(BusinessID) ON DELETE CASCADE
);

CREATE INDEX INDEX7 On Business_Subcategories (SubCName);
CREATE INDEX INDEX8 On Business_Subcategories (BusinessID);

CREATE Table Business_Att (
    BusinessID VARCHAR(30),
    AName VARCHAR(50),
    PRIMARY KEY(BusinessID, AName),
    FOREIGN KEY (BusinessID) REFERENCES Business(BusinessID) ON DELETE CASCADE
);

CREATE INDEX INDEX9 On Business_Att(AName);
CREATE INDEX INDEX10 On Business_Att(BusinessID);

CREATE TABLE Review (
    ReviewID VARCHAR(30),
    Stars INT CHECK (Stars > 0 and Stars < 6),
    Author VARCHAR(30),
    PublishDate Date,
    BusinessID  VARCHAR(30),
    Votes int DEFAULT 0,
    PRIMARY KEY (ReviewID),
    FOREIGN KEY (Author) REFERENCES Users(UserID) ON DELETE SET NULL,
    FOREIGN KEY (BusinessID) REFERENCES Business(BusinessID) ON DELETE CASCADE
);

CREATE INDEX INDEX3 ON REVIEW (STARS);
CREATE INDEX INDEX4 ON REVIEW (PublishDate);
CREATE INDEX INDEX14 ON REVIEW (Votes);
CREATE INDEX INDEX15 ON REVIEW (BusinessID);

CREATE TABLE Checkin (
    ctype varchar(10),
    BusinessID VARCHAR(30),
    info varchar(1500),
    FOREIGN KEY (BusinessID) REFERENCES Business(BusinessID) ON DELETE CASCADE
);





