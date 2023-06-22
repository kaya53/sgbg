import "./App.css";
import Navbar from "./components/bars/Navbar";
import Router from "./Router";

function App() {
  return (
    // pb-16: bottom navbar 아래로 본문이 들어가는 것을 막기 위함 0921 임지민
    <div className="App pb-16">
      <Router />
      <Navbar />
    </div>
  );
}

export default App;
