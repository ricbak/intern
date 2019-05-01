import React from 'react';

import { Link } from "react-router-dom";

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Spinner from 'react-bootstrap/Spinner'


class RegisterForm extends React.Component {

    constructor(props){
        super(props);
        this.state = {loading : false};
    }

    handleChange(e) {
        console.log(e.target.value);
        this.setState({ 
            name: e.target.value,
            loading: false
         });
    }

    handleSubmit(e) {
        this.setState( {
            loading : true
        });

        e.preventDefault();

        var name = this.state.name;
        const axios = require('axios');
        
        console.log(name);        

        axios.post('http://localhost:8080/person/register', {
            name: name
        })
            .then(function (response) {
                this.props.setRegister(name);
                console.log("response");
            })
            .catch(function (error) {
                console.log("error");
            });

    }

    render() {
        const loading = this.state.loading;


        const waiting = loading ?
        <Spinner animation="border" role="status">
            <span className="sr-only">Loading...</span>
        </Spinner> : '';

        return (
            <Form onSubmit={(e) => this.handleSubmit(e)}>
                <Card>
                    <Card.Body className="text-center">
                        <Form.Group controlId="name">
                            <Card.Title><Form.Label>Vul uw naam in om te registreren.</Form.Label></Card.Title>
                            <Form.Control type="text" placeholder="Uw naam" onChange={(e) => this.handleChange(e)} />
                        </Form.Group>
                        <Link to="/">
                            <Button variant="secondary">Annuleren</Button>
                        </Link>
                        <Button variant="primary" type="submit">Registreren</Button>
                        {loading}
                    </Card.Body>
                </Card>
            </Form>
        );
    }
}

export default RegisterForm;