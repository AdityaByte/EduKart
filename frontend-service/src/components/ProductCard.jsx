import React from 'react';

const ProductCard = ({ image, title, description, price, badge }) => {

  let my_img = "https://imgs.search.brave.com/QeIDF2BqwP8ddMzoRaweQfNh0wp_8YlaPFn7ngD-Kpo/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJjYXQuY29t/L3cvZnVsbC9kLzAv/ZC8xMzI2MjMwLTE5/MjB4MTIwMC1kZXNr/dG9wLWhkLWJvb2st/YmFja2dyb3VuZC5q/cGc"

  return (
    <div className="relative bg-white rounded-2xl shadow-xl overflow-hidden transform transition duration-500 hover:scale-105 hover:shadow-2xl cursor-pointer">

      {badge && (
        <span className="absolute top-4 left-4 bg-gradient-to-r from-indigo-500 to-purple-500 text-white text-xs font-semibold px-3 py-1 rounded-full shadow-md">
          {badge}
        </span>
      )}

      <div className="w-full h-56 bg-gray-100 overflow-hidden">
        <img
          src={image != null ? image : my_img}
          alt={title}
          className="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
        />
      </div>

      {/* Product Info */}
      <div className="p-6 flex flex-col gap-3">
        <h3 className="text-xl font-bold text-gray-900">{title}</h3>
        <p className="text-gray-600 text-sm">{description}</p>
        <p className="text-lg font-extrabold text-gray-900">${price}</p>

        <button className="mt-4 px-6 py-2 bg-gradient-to-r from-blue-500 to-blue-700 text-white rounded-xl font-semibold shadow-lg hover:shadow-2xl transition-all duration-300">
          View Product
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
