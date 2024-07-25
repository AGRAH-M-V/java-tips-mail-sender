import React, { useState } from 'react';
import axios from 'axios';
import confetti from 'canvas-confetti'; // Import the confetti library

const SubscriptionForm = () => {
  const [email, setEmail] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8080/api/subscription/subscribe', { email });
      if (response.data.id) {
        setSuccessMessage('Thank you for subscribing! Check your email for daily Java tips.');
        setErrorMessage('');
        setEmail('');
        triggerFireworks();
      } else {
        setErrorMessage('Subscription failed. Please try again.');
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data);
      } else {
        setErrorMessage('There was an error subscribing. Please try again.');
      }
      setSuccessMessage('');
    } finally {
      setLoading(false);
      setTimeout(() => setErrorMessage(''), 5000);
      setTimeout(() => setSuccessMessage(''), 5000);
    }
  };

  const triggerFireworks = () => {
    var duration = 15 * 1000;
    var animationEnd = Date.now() + duration;
    var defaults = { startVelocity: 30, spread: 360, ticks: 60, zIndex: 0 };

    function randomInRange(min, max) {
      return Math.random() * (max - min) + min;
    }

    var interval = setInterval(function() {
      var timeLeft = animationEnd - Date.now();

      if (timeLeft <= 0) {
        return clearInterval(interval);
      }

      var particleCount = 50 * (timeLeft / duration);
      confetti({ ...defaults, particleCount, origin: { x: randomInRange(0.1, 0.3), y: Math.random() - 0.2 } });
      confetti({ ...defaults, particleCount, origin: { x: randomInRange(0.7, 0.9), y: Math.random() - 0.2 } });
    }, 250);
  };

  return (
    <div className="subscription-form-container">
      <header>
        <h1>Subscribe to Daily Java Programming Tips</h1>
        <h2>Stay updated with the latest Java programming tips delivered to your inbox every day!</h2>
      </header>
      <main>
        <p>Enter your email address below and click 'Subscribe' to receive daily Java programming tips.</p>
        <form onSubmit={handleSubmit}>
          <input
            type="email"
            id="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email address"
            required
          />
          <button type="submit" disabled={loading}>
            {loading ? <span className="loader"></span> : 'Subscribe'}
          </button>
        </form>
        {successMessage && <p className="success-message">{successMessage}</p>}
        {errorMessage && <p className="error-message">{errorMessage}</p>}
      </main>
      <footer>
        <p>We respect your privacy. Your email address will only be used to send you daily Java tips.</p>
        <p>Contact us if you have any questions or issues with subscribing.</p>
      </footer>
    </div>
  );
};

export default SubscriptionForm;
