import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { vi } from 'vitest'
import RegisterPage from '../pages/RegisterPage'
import * as authService from '../services/authService'

// Mock the entire authService module
// This means no real HTTP calls are made during tests
vi.mock('../services/authService')

describe('RegisterPage', () => {

  // Runs before each test — reset all mocks to clean state
  beforeEach(() => {
    vi.clearAllMocks()
  })

  //  TEST 1: Form Renders Correctly 
  test('should render all form fields', () => {

    render(<RegisterPage />)

    // Check all fields are present on screen
    expect(screen.getByPlaceholderText('Pranay Babu')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('pranay@example.com')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('At least 6 characters')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: /create account/i })).toBeInTheDocument()
  })

  //  TEST 2: Validation Errors on Empty Submit 
  test('should show validation errors when form is submitted empty', async () => {
    const user = userEvent.setup()

    render(<RegisterPage />)

    // Click submit without filling anything
    await user.click(screen.getByRole('button', { name: /create account/i }))

    // All error messages should appear
    expect(screen.getByText('Name is required')).toBeInTheDocument()
    expect(screen.getByText('Email is required')).toBeInTheDocument()
    expect(screen.getByText('Password is required')).toBeInTheDocument()
  })

  //  TEST 3: Invalid Email Validation 
  test('should show error for invalid email format', async () => {
    const user = userEvent.setup()

    render(<RegisterPage />)

    // Type invalid email and submit
    await user.type(screen.getByPlaceholderText('pranay@example.com'), 'notanemail')
    await user.click(screen.getByRole('button', { name: /create account/i }))

    expect(screen.getByText('Please enter a valid email')).toBeInTheDocument()
  })

  //  TEST 4: Short Password Validation 
  test('should show error when password is less than 6 characters', async () => {
    const user = userEvent.setup()

    render(<RegisterPage />)

    await user.type(screen.getByPlaceholderText('At least 6 characters'), '123')
    await user.click(screen.getByRole('button', { name: /create account/i }))

    expect(screen.getByText('Password must be at least 6 characters')).toBeInTheDocument()
  })

  //  TEST 5: Successful Registration 
  test('should show success message on successful registration', async () => {
    const user = userEvent.setup()

    // Mock the API call to return success
    authService.registerUser.mockResolvedValue({
      message: 'User registered successfully!'
    })

    render(<RegisterPage />)

    // Fill the form correctly
    await user.type(screen.getByPlaceholderText('Pranay Babu'), 'Pranay Babu')
    await user.type(screen.getByPlaceholderText('pranay@example.com'), 'pranay@example.com')
    await user.type(screen.getByPlaceholderText('At least 6 characters'), 'securePassword123')
    await user.click(screen.getByRole('button', { name: /create account/i }))

    // Wait for async API call to complete
    await waitFor(() => {
      expect(screen.getByText('User registered successfully!')).toBeInTheDocument()
    })
  })

  // TEST 6: Duplicate Email Error 
  test('should show error when email is already taken', async () => {
    const user = userEvent.setup()

    // Mock API to simulate duplicate email error from backend
    authService.registerUser.mockRejectedValue({
      response: {
        data: { message: 'Email is already taken' }
      }
    })

    render(<RegisterPage />)

    await user.type(screen.getByPlaceholderText('Pranay Babu'), 'Pranay Babu')
    await user.type(screen.getByPlaceholderText('pranay@example.com'), 'pranay@example.com')
    await user.type(screen.getByPlaceholderText('At least 6 characters'), 'securePassword123')
    await user.click(screen.getByRole('button', { name: /create account/i }))

    await waitFor(() => {
      expect(screen.getByText('Email is already taken')).toBeInTheDocument()
    })
  })

  //  TEST 7: Button Disabled While Loading 
  test('should disable button while API call is in progress', async () => {
    const user = userEvent.setup()

    // Mock API to take time (never resolves in this test)
    authService.registerUser.mockImplementation(() => new Promise(() => {}))

    render(<RegisterPage />)

    await user.type(screen.getByPlaceholderText('Pranay Babu'), 'Pranay Babu')
    await user.type(screen.getByPlaceholderText('pranay@example.com'), 'pranay@example.com')
    await user.type(screen.getByPlaceholderText('At least 6 characters'), 'securePassword123')
    await user.click(screen.getByRole('button', { name: /create account/i }))

    // Button should be disabled and text should change
    expect(screen.getByRole('button', { name: /creating account/i })).toBeDisabled()
  })

  //  TEST 8 : password Pattern 
  test('should show error when password fails complexity check', async () => {
  const user = userEvent.setup()

  render(<RegisterPage />)

  // long enough but no uppercase or number
  await user.type(screen.getByPlaceholderText('At least 6 characters'), 'alllowercase')
  await user.click(screen.getByRole('button', { name: /create account/i }))

  expect(screen.getByText('Password must contain at least one uppercase letter, one lowercase letter, and one number')).toBeInTheDocument()
})
})