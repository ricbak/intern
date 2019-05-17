import React from 'react';
import { Link } from "react-router-dom";

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import CustomCardBody from './CustomCardBody'

function CustomCard(props) {
    return (
        <Card>
            <Card.Body className="text-center">
                <Card.Title>{props.card.title}</Card.Title>
                <CustomCardBody input = {props.card.input} text = {props.card.text}/>
            </Card.Body>
        </Card>
    );
}

export default CustomCard;