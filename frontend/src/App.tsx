import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { StudentList } from "./pages/StudentList";
import { StudentDetail } from "./pages/StudentDetail";
import { StudentRegister } from "./pages/StudentRegister";
import { StudentFilter } from "./pages/StudentFilter";
import { StudentUpdate } from "./pages/StudentUpdate";

export const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StudentList />} />
        <Route path="/students/register" element={<StudentRegister />} />
        <Route path="/students/filter" element={<StudentFilter />} />
        <Route path="/students/:id" element={<StudentDetail />} />
        <Route path="/students/:id/edit" element={<StudentUpdate />} />
      </Routes>
    </BrowserRouter>
  );
};
