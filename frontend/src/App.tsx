import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { StudentList } from "./pages/StudentList";

export const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StudentList />} />
      </Routes>
    </BrowserRouter>
  );
};
