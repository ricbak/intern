import React from 'react';
import { Link } from "react-router-dom";

import Button from 'react-bootstrap/Button';

function CancelProceed() {
    return (
        <div>
            <Link to="/">
                <Button variant="secondary">Annuleren</Button>
            </Link>
            <Button variant="primary" type="submit">Opslaan</Button>
        </div>

    )
}

export default CancelProceed;