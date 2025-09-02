import React from 'react'

const ReviewCard = ({ clientImage, name, title, reviewText }) => {
  return (
    <div className="max-w-sm w-full bg-white shadow-lg rounded-2xl p-6 transition duration-300 ease-in-out hover:scale-105">
      {/* Client image */}
      <div className="flex items-center space-x-4">
        <img
          src={clientImage}
          alt={name}
          className="w-16 h-16 rounded-full object-cover border-2 border-blue-500"
        />
        <div>
          <h3 className="text-lg font-semibold text-gray-800">{name}</h3>
          <p className="text-sm text-gray-500">{title}</p>
        </div>
      </div>

      {/* Review text */}
      <p className="mt-4 text-gray-600 text-base leading-relaxed">
        “{reviewText}”
      </p>
    </div>
  )
}

export default ReviewCard
