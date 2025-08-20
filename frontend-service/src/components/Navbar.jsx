import React, { useState, useEffect } from "react";
import SearchIcon from "./icons/SearchIcon";
import AccountIcon from "./icons/AccountIcon";
import CartIcon from "./icons/CartIcon";
import MenuIcon from "./icons/MenuIcon";

const Navbar = () => {
  const nav_items = [
    { link: "home", heading: "Home" },
    { link: "about", heading: "About" },
  ];

  const [theme, setTheme] = useState(() => {
    return localStorage.getItem("theme") || "light";
  });

  useEffect(() => {
    document.documentElement.setAttribute("data-theme", theme);
    localStorage.setItem("theme", theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme((prev) => (prev === "light" ? "dark" : "light"));
  };

  return (
    <nav className="flex items-center justify-between px-8 py-4 bg-[var(--navbar-bg)] text-[var(--navbar-fg)] w-full fixed top-0 shadow-md z-50">
      {/* Left: Nav Items */}
      <div className="flex gap-8 items-center justify-center w-[20%]">
        {nav_items.map((item, index) => (
          <a
            key={index}
            href={item.link}
            className="relative font-medium hover:text-[var(--foreground-color)] transition duration-300
                       after:content-[''] after:absolute after:left-0 after:-bottom-1 after:h-[2px] after:w-0
                       after:bg-[var(--foreground-color)] after:transition-all after:duration-300 hover:after:w-full"
          >
            {item.heading}
          </a>
        ))}
      </div>

      {/* Center: Logo */}
      <div>
        <h1 className="text-2xl font-bold bg-clip-text cursor-pointer">
          EduKart.
        </h1>
      </div>

      {/* Right: Icons + Toggle */}
      <div className="flex items-center gap-6 w-[20%] justify-center">
        <button
          onClick={toggleTheme}
          className="px-3 py-2 rounded-lg bg-[var(--toggle-bg)] text-[var(--toggle-fg)] font-medium shadow hover:scale-105 transition"
        >
          {theme === "light" ? "🌙" : "☀️"}
        </button>
        <button className="hover:text-[var(--foreground-color)] transition">
          <SearchIcon />
        </button>
        <button className="hover:text-[var(--foreground-color)] transition">
          <AccountIcon />
        </button>
        <button className="hover:text-[var(--foreground-color)] transition">
          <CartIcon />
        </button>
        <button className="hover:text-[var(--foreground-color)] transition md:hidden">
          <MenuIcon />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
