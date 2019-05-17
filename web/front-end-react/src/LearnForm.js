import React from 'react';
import Card from 'react-bootstrap/Card';
import Dropdown from 'react-bootstrap/Dropdown';
import Form from 'react-bootstrap/Form';
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner'


import CancelProceed from './CancelProceed'

class LearnForm extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            initial: true,
            personAzureId: "",
            personName: "Naam",
            persons: [],
            busy: false,
        }

        this.setPerson = this.setPerson.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        var initial = this.state.initial;
        if (initial) {
            this.setState({ initial: false })

            const axios = require('axios');

            axios.get('http://localhost:8080/admin/person/list')
                .then((response) => {
                    const persons = response.data;

                    this.setState({
                        persons
                    })
                })
                .catch((error) => {
                })
                .then(() => {
                });
        }
    }

    setPerson(name, id, event) {

        this.setState({
            personAzureId: id,
            personName: name,
        });

    }
    dropDownItem(person) {
        console.log(person);
        return (
            <Dropdown.Item key={person.azureId} eventKey={person.azureId} onSelect={this.setPerson.bind(this, person.name)}>{person.name}</Dropdown.Item>
        )
    }

    dropDownList() {
        const name = this.state.personName;

        const listItems = this.state.persons.map((person) =>
            this.dropDownItem(person)
        );

        return (
            <Dropdown>
                <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                    {name}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                    {listItems}
                </Dropdown.Menu>
            </Dropdown>

        )
    }

    handleSubmit(e) {
        e.preventDefault();

        const personAzureId = this.state.personAzureId;

        if (personAzureId.length > 0) {




            this.setState({
                busy: true,
            });
            const azureId = this.state.personAzureId;
            const personName = this.state.personName;


            console.log(azureId);

            const axios = require('axios');

            axios.get('http://127.0.0.1:5000/register/' + azureId)
                .then((response) => {
                    console.log(response.data);
                    this.props.setName(personName, response.data);
                })
                .catch((error) => {
                })
                .then(() => {
                });
        }
    }

    render() {
        const busy = this.state.busy;
        const waiting = busy ?
            <div>
                <Spinner animation="border" role="status">
                    <span className="sr-only">Loading...</span>
                </Spinner>
                <p>Kijk naar de camera</p>
            </div>
            : ''

            ;

        console.log(this.state.persons);
        const dropdown = this.dropDownList();

        return (

            <Form onSubmit={(e) => this.handleSubmit(e)}>
                <Card>
                    <Card.Body className="text-center">
                        <Form.Group controlId="name">
                            <Card.Title><Form.Label>Kies uw naam</Form.Label></Card.Title>
                            {dropdown}
                        </Form.Group>
                        <div>
                            <Link to="/">
                                <Button variant="secondary">Annuleren</Button>
                            </Link>
                            <Button variant="primary" type="submit">Bijleren</Button>
                        </div>
                        {waiting}
                    </Card.Body>
                </Card>
            </Form>
        );
    }
}

export default LearnForm;