import React from "react";

const Tag = ({ text }) => {
  return (
    <span
      className="
        inline-block
        rounded-full
        px-5
        py-2
        text-sm
        font-medium
        text-white
        bg-[var(--foreground-color)]
        shadow-md
        hover:shadow-lg
        transition
        duration-300
        ease-in-out
        cursor-default
        animate-fadeIn
      "
    >
      {text}
    </span>
  );
};

export default Tag;
