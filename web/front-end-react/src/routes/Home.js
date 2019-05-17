import React from 'react';
import { Link } from "react-router-dom";

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import CustomCard from '../CustomCard'

function Home() {

    const card = {
        title: "title",
        bodyText: "",
        input: true,
        identified: false,
    }
        // return (
        // <CustomCard card = {card}/>

    return (
        <Card>
            <Card.Body className="text-center">
                <Card.Title>Maak uw keuze.</Card.Title>
                <Link to="/register">
                        <Button variant="primary">Registreren</Button>
                    </Link>
                    <Link to="/learn">
                        <Button variant="primary">Bijleren</Button>
                    </Link>
                    <Link to="/identify">
                        <Button variant="primary">Identificeren</Button>
                    </Link>                        
            </Card.Body>
            <Card.Footer>

            </Card.Footer>
        </Card>
    );
}

export default Home;