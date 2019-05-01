import React from 'react';
import { Link } from "react-router-dom";

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function RegisterCompleted(props) {
    return (

        <Card>
            <Card.Body className="text-center">
                <Card.Text>
                    {props.name} , U kunt naar binnen lopen.
                </Card.Text>
            </Card.Body>
        </Card>

    );
}

export default RegisterCompleted;