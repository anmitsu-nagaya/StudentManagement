import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { StudentList } from "./pages/StudentList";
import { StudentDetail } from "./pages/StudentDetail";

export const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StudentList />} />
        <Route path="/students/:id" element={<StudentDetail />} />
      </Routes>
    </BrowserRouter>
  );
};
