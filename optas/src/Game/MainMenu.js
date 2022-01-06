import './GameStyle.css';
import { Link } from "react-router-dom";

function Menu()
{
    return(
        <div className="middle">
            <h2 className="text-nowrap">Žaidimas Hangman</h2>
            <div className="d-flex flex-column text-nowrap pt-3">
                <Link to="/game" className="text-decoration-none">Pradėti žaidimą</Link>
                <p>Instrukcijos</p>
             </div>
        </div>
    );
}
export default Menu