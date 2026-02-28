import { BrowserRouter, Routes, Route } from "react-router-dom";
import { StudentList } from "./pages/StudentList";
import { Login } from "./pages/Login";
import { Courses } from "./pages/Courses";

export const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/students" element={<StudentList />} />
        <Route path="/courses" element={<Courses />} />
      </Routes>
    </BrowserRouter>
  );
};
