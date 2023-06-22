import { Link } from "react-router-dom";

const BtnExitToHome = () =>{
    return( 
        <div className="inline-block">
            <Link to="/">
                <button className="text-2xl font-black mr-3">X</button>
            </Link>
        </div>
    );
};

export default BtnExitToHome