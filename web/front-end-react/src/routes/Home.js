import React from 'react';
import { Link } from "react-router-dom";

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function Home() {
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
            </Card.Body>
        </Card>

    );
}

export default Home;