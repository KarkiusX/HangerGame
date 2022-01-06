import './GameStyle.css';
import { Link } from "react-router-dom";

function Menu()
{
    return(
        <div className="middle">
            <h2 className="text-nowrap">Žaidimas Hangman</h2>
            <div className="d-flex flex-column text-nowrap pt-3">
                <Link to="/game" className="text-decoration-none">
                <button type="button" class="btn btn-outline-primary">Pradėti žaidimą</button>
                </Link>
                <h4 className='pt-5 pb-3'>Instrukcijos:</h4>

                <p className="fw-bold">Žodis turi būti atspėjamas per mažiau nei 10 bandymų</p>
                <p className="fw-bold">Jeigu bandote rašyti tą pačia raidę, užsiskaito kaip bandymas</p>
             </div>
        </div>
    );
}
export default Menu