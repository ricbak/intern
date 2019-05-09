import React from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Dropdown from 'react-bootstrap/Dropdown';

class Learn extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            persons: [],
        }
    }

    fetchPersons() {
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

    learnPerson(e) {

        console.log("hoi");

        //     const axios = require('axios');

        //     axios.get('http://localhost:8080/person/face/add')
        //         .then((response) => {

        //         })
        //         .catch((error) => {
        //         })
        //         .then(() => {
        //         });
    }

    dropDownItem() {
        return (
            <Dropdown.Item ></Dropdown.Item>
        )
    }

    dropDownList() {
        return (
            <Dropdown>
                <Dropdown.Toggle variant="success" id="dropdown-basic">
                    Dropdown Button
            </Dropdown.Toggle>

                <Dropdown.Menu>

                    <div onClick={this.learnPerson.bind(this, 'work')}>
                        <Dropdown.Item >Another action</Dropdown.Item>
                    </div>
                </Dropdown.Menu>
            </Dropdown>

        )
    }

    render() {
        const dropdown = this.dropDownList();

        return (
            <Card>
                <Card.Body className="text-center">
                    <Card.Title>Kies uw naam.</Card.Title>
                    {dropdown}
                </Card.Body>
            </Card>
        );
    }
}

export default Learn;