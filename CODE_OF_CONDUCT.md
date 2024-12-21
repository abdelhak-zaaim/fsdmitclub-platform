# Contributing to [FSDM IT Club Platform]

Thank you for showing interest in contributing to the **FSDM IT Club Platform** project! We welcome contributions from everyone, whether you're adding code, fixing bugs, updating documentation, or suggesting features. By contributing, you are helping us build something valuable for the community!

Please follow the guidelines below to ensure a smooth process for everyone. 🙌

---

## **Getting Started**

### 1. Check for Issues
- Look at the [Issues](https://github.com/Fsdm-IT-AI-Club/fsdmitclub-platform/issues) tab to see the current issues or feature requests you can work on.
- If you want to work on an issue, comment on it so others know it's being handled.
- If your contribution doesn’t align with an open issue, please create one and get it approved by maintainers before starting.

---

## **How to Contribute**

### 1. Fork the Repository
- Click on the “Fork” button at the top right of this repository to create your copy of the project.

### 2. Clone Your Fork
- Clone the forked repository to your local machine using the following command:
  ```bash
  git clone https://github.com/<your-username>/fsdmitclub-platform.git
  ```
- Replace `<your-username>` with your GitHub username.

### 3. Configure Upstream
- Set up the original repository as `upstream` to keep your fork updated:
  ```bash
  git remote add upstream https://github.com/Fsdm-IT-AI-Club/fsdmitclub-platform.git
  ```

### 4. Create a Branch
- Work on your features or fixes in a separate branch. This helps keep your changes isolated from others:
  ```bash
  git checkout -b feature/your-feature-name
  ```

### 5. Make Changes
- Make your changes locally. Ensure proper coding standards are followed:
    - Java: Follow industry Java conventions.
    - Thymeleaf templates: Create clean and readable frontend code.
    - Other standards for consistency.

### 6. Test Your Changes
- Run and test your changes thoroughly to ensure no breaking issues.
- Test unit and integration tests using Maven:
  ```bash
  mvn test
  ```

### 7. Commit Your Changes
- Use descriptive commit messages to explain what your changes do:
  ```bash
  git add .
  git commit -m "Brief description of the changes"
  ```

### 8. Push to Your Fork
- Push your branch to **your fork**:
  ```bash
  git push origin feature/your-feature-name
  ```

### 9. Create a Pull Request
- Open a pull request (PR) from your branch on your forked repository to `main` in the original repository.
- Fill out the PR description according to the provided template. Explain:
    - What the issue is (if applicable)
    - How you resolved it
    - Steps to verify your changes

---

## **Contribution Guidelines**

Please follow these guidelines to keep the project organized and maintain high-quality contributions:

### 1. Code Style
- Follow the standard Java conventions (e.g., proper indentations, meaningful variable/method names).
- Use comments where necessary to explain complex logic.

### 2. Documentation
- Update relevant documentation (e.g., README, code comments) for any new features or fixes.
- This helps others understand the purpose and function of your changes.

### 3. Testing
- Ensure all code changes are tested before submitting a PR.
- Contribute unit or integration tests for new features.

### 4. Respect Version Control
- Do not commit directly to the `main` branch.
- All commits should be associated with a specific issue and handled in branches similar to `feature/issue-number`.

---

## **Code of Conduct**

To make contributing a pleasant experience for everyone:
1. Be respectful and inclusive in your communication.
2. Avoid dismissive or unkind comments.
3. Collaborate constructively—feedback should be helpful and not personal.
4. Respect everyone's time and contributions.

For more information, see the full [Code of Conduct](CODE_OF_CONDUCT.md).

---

## **Where to Get Help**

If you get stuck:
- Ask for help in the **FSDM IT Club forum/chat** (if applicable).
- Open a discussion in the [Discussions tab](https://github.com/Fsdm-IT-AI-Club/fsdmitclub-platform/discussions).
- Tag maintainers in comments for complex issues.

---

## **Project Setup**

### Prerequisites
Ensure the required dependencies are installed:
- **JDK 21+**
- **Maven**
- **Docker**

### Build and Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/Fsdm-IT-AI-Club/fsdmitclub-platform.git
   cd fsdmitclub-platform
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

3. Run the project:
   ```bash
   mvn spring-boot:run
   ```

4. Open the application in your browser:
   ```
   http://localhost:80/
   ```

5. (Optional) Run with Docker:
    - Build the Docker image:
      ```bash
      docker build -t fsdmitclub:latest .
      ```
    - Run the container:
      ```bash
      docker run -p 80:80 fsdmitclub:latest
      ```

---

## **Acknowledgments**

Thanks for contributing and making the **FSDM IT Club Platform** valuable to the entire community! We're excited to see your ideas and improvements. Feel free to reach out if you have any questions or need assistance during the process.

Happy coding! 🚀