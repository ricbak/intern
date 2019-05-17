import React from 'react';

class PersonList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            persons: []
        }
    }

    componentDidMount() {
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

    render() {
        const p = this.state.persons;
        console.log("render persons");
        console.log(p);
        return (
            <List persons={p}></List>
        );
    }
}

function ListItem(props) {
    console.log("list item")
    console.log(props);
    return (
        <tr>
            <th scope="row">{props.person.id}</th>
            <td>{props.person.name}</td>
            <td>{props.person.azureId}</td>
            <td>{props.person.source}</td>
        </tr>

    )
}

function List(props) {
    console.log("list persons");
    console.log(props.persons);
    
    const persons = props.persons;
    const listItems = persons.map((person) =>
        <ListItem key={person.id}
            person={person} />

    );

    return (
        <table className="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Id</th>
                    <th scope="col">Source</th>
                </tr>
            </thead>
            <tbody>
                {listItems}
            </tbody>
        </table>
    )
}

export default PersonList;